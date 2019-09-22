#!/usr/bin/env bash

echo -ne "\033[0;32m"
echo 'Updating jvm dependencies. This will take about five minutes.'
echo -ne "\033[0m"

# update this to move to later versions of this repo:
# https://github.com/johnynek/bazel-deps
GITSHA="8c929d411f1434c835cd9801977a4ab396c3f1fc"

set -e

SCRIPTS_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
REPO_ROOT="$( cd "${1:-$(cd "$SCRIPTS_DIR" && git rev-parse --show-toplevel)}" && pwd )"

BAZEL_DEPS_PATH="$HOME/.bazel-deps-cache/$(basename $REPO_ROOT)"
BAZEL_DEPS_REPO_PATH="$BAZEL_DEPS_PATH/bazel-deps"
BAZEL_DEPS_WORKSPACE="$BAZEL_DEPS_REPO_PATH/WORKSPACE"

if [ ! -f "$BAZEL_DEPS_WORKSPACE" ]; then
  mkdir -p "$BAZEL_DEPS_PATH"
  cd "$BAZEL_DEPS_PATH"
  git clone https://github.com/johnynek/bazel-deps.git
fi

cd "$BAZEL_DEPS_REPO_PATH"
git reset --hard $GITSHA 2>/dev/null || (git fetch && git reset --hard $GITSHA)
set +e
bazel --bazelrc=/dev/null run \
  --incompatible_disable_deprecated_attr_params=false \
  //:parse -- \
  generate \
  -r "$REPO_ROOT" \
  -s 3rdparty/workspace.bzl \
  -d dependencies.yaml \
  --target-file 3rdparty/target_file.bzl \
  --disable-3rdparty-in-repo
RET_CODE=$?
set -e

if [ $RET_CODE == 0 ]; then
  echo "Success, going to format files"
else
  echo "Failure: rolling back changes made to 3rdparty/jvm and 3rdparty/workspace.bzl"
  cd "$REPO_ROOT"
  git checkout 3rdparty
  exit $RET_CODE
fi

# now reformat the dependencies to keep them sorted
bazel --bazelrc=/dev/null run //:parse -- format-deps -d "$REPO_ROOT"/dependencies.yaml -o
cd "$SCRIPTS_DIR"