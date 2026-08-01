SUMMARY = "AirPlay mirroring and audio server"
DESCRIPTION = "UxPlay is an AirPlay screen-mirroring and audio server using GStreamer."
HOMEPAGE = "https://github.com/FDH2/UxPlay"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "git://github.com/FDH2/UxPlay.git;protocol=https;branch=master"
SRCREV = "21eef8df25d91e12635c36d8176ad192725baca2"

S = "${WORKDIR}/git"

DEPENDS = " \
    avahi \
    libplist \
    openssl \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
"

inherit cmake pkgconfig

EXTRA_OECMAKE = " \
    -DNO_X11_DEPS=ON \
    -DNO_MARCH_NATIVE=ON \
"

# Bluetooth beacon support is not required for our Avahi-based project.
do_install:append() {
    rm -f ${D}${bindir}/uxplay-beacon.py
    rm -f ${D}${bindir}/uxplay_beacon_module_*.py
    rm -f ${D}${mandir}/man1/uxplay-beacon.1
}
