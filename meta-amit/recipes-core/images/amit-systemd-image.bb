SUMMARY = "AMIT Bullet systemd and Qt5 image"
DESCRIPTION = "Raspberry Pi 4 image using systemd with Qt5, middleware, multimedia, networking and AMIT applications."

LICENSE = "MIT"

require recipes-core/images/core-image-base.bb

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL:append = " \
    packagegroup-amit-base \
    packagegroup-amit-middleware \
    packagegroup-amit-qt5 \
    packagegroup-amit-observability \
    uxplay \
    gstreamer1.0 \
    gstreamer1.0-plugins-base-meta \
    gstreamer1.0-plugins-good-meta \
    gstreamer1.0-plugins-bad-meta \
    gstreamer1.0-libav \
"
