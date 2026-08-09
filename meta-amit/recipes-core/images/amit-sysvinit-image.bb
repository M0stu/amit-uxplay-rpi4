SUMMARY = "AMIT Bullet SysVinit image"
DESCRIPTION = "Raspberry Pi 4 image using SysVinit without Qt5."

LICENSE = "MIT"

require recipes-core/images/core-image-base.bb

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL:append = " \
    packagegroup-amit-base \
    packagegroup-amit-middleware \
    packagegroup-amit-observability \
    uxplay \
    gstreamer1.0 \
    gstreamer1.0-plugins-base-meta \
    gstreamer1.0-plugins-good-meta \
    gstreamer1.0-plugins-bad-meta \
    gstreamer1.0-libav \
"
