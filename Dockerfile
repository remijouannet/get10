FROM ubuntu:16.04

# Install java8
RUN apt-get update && apt-get install --force-yes -y openjdk-8-jdk

# Install Deps
RUN dpkg --add-architecture i386 && apt-get update
RUN apt-get install -y --force-yes unzip expect git wget libc6-i386 make bzip2 \
        lib32stdc++6 lib32gcc1 lib32ncurses5 lib32z1 python curl libqt5widgets5 

# Clean
RUN apt-get clean && rm -fr /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Install github-releases
RUN wget https://github.com/aktau/github-release/releases/download/v0.7.2/linux-amd64-github-release.tar.bz2
RUN tar xvf linux-amd64-github-release.tar.bz2
RUN mv bin/linux/amd64/github-release /bin/ && rm -rf bin/linux && rm linux-amd64-github-release.tar.bz2

# Install Android SDK
RUN mkdir -p /opt/android && cd /opt/android \
    && wget --output-document=android.zip --quiet https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip \
    && unzip android.zip && rm -f android.zip

# Setup environment
ENV ANDROID_HOME /opt/android
ENV PATH ${PATH}:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools

# Install sdk elements
run yes | sdkmanager --update --licenses
run yes | sdkmanager \
    "platforms;android-22" \
    "platform-tools" \
    "extras;google;m2repository" \
    "extras;android;m2repository" \
    "build-tools;21.1.2" \
    "extras;google;google_play_services" \
    "add-ons;addon-google_apis-google-24"

# Go to workspace
RUN mkdir -p /workspace
COPY ./ /workspace
WORKDIR /workspace

ENTRYPOINT ["make"]
