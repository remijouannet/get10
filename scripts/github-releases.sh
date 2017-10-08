#!/bin/bash

set +e

version=$(git describe --abbrev=0 --tags)

which github-release || echo 'please install the tool github-releases'

github-release info \
    --user remijouannet \
    --repo get10 \
    --tag $version

if [ $? != 0 ]
then
    github-release release \
        --user remijouannet \
        --repo get10 \
        --tag $version \
        --pre-release \
        --name "$version-hyper-alpha-yolo-experimental" \
        --description "risks of explosion" || echo "failed to create release for $version"
fi

cd pkg/
mv app-ci.apk get10.apk
echo "upload get10.apk"
github-release upload \
    --user remijouannet \
    --name "$version-hyper-alpha-yolo-experimental" \
    --repo get10 \
    --file "get10.apk" \
    --replace \
    --tag $version || echo "failed to upload app-ci.apk"
