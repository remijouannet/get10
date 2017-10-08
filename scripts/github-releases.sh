#!/bin/bash

set +e

version=$(git describe --abbrev=0 --tags)

which github-release || echo 'please install the tool github-releases'

github-release info \
    --user remijouannet \
    --repo get10 \
    --tag $version || echo "the release doesn't exist"

if [ $? != 0 ]
then
    github-release release \
        --user remijouannet \
        --repo get10 \
        --tag $version \
        --draft \
        --pre-release \
        --name "$version-hyper-alpha-yolo-experimental" \
        --description "risks of explosion" \
        --target $version || echo "failed to create release for $version"
fi

cd pkg/
echo "upload app-ci.apk"
github-release upload \
    --user remijouannet \
    --name "get10.apk" \
    --repo get10 \
    --file "app-ci.apk" \
    --replace \
    --tag $version || echo "failed to upload app-ci.apk"
