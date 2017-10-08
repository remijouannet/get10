VERSION=$$(git describe --abbrev=0 --tags)
PWD=$$(pwd)

pkg:
	rm -rf ./build/*
	rm -rf ./app/build/outputs/apk/*
	./gradlew assembleCi

release:
	scripts/github-releases.sh

docker-image:
	docker build -t android-get10:$(VERSION) .

docker-build:
	docker run \
		-v $(PWD)/pkg:/workspace/app/build/outputs/apk \
		android-get10:$(VERSION) pkg

docker-release:
	docker run \
		-v $(PWD)/pkg:/workspace/pkg \
		-e "GITHUB_TOKEN=$(GITHUB_TOKEN)" \
		android-get10:$(VERSION) release

.PHONY: pkg release
