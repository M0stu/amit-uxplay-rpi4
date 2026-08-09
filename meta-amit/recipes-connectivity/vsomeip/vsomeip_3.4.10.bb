SUMMARY = "COVESA vSomeIP middleware"
DESCRIPTION = "Implementation of the SOME/IP protocol for inter-process and network communication."
HOMEPAGE = "https://github.com/COVESA/vsomeip"

LICENSE = "MPL-2.0 & BSD-3-Clause"

LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=9741c346eef56131163e13b9db1241b3 \
    file://${WORKDIR}/googletest/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a \
"

SRC_URI = " \
    git://github.com/COVESA/vsomeip.git;protocol=https;nobranch=1;name=vsomeip \
    git://github.com/google/googletest.git;protocol=https;nobranch=1;name=googletest;destsuffix=googletest \
"

SRCREV_vsomeip = "02c199dff8aba814beebe3ca417fd991058fe90c"
SRCREV_googletest = "f8d7d77c06936315286eb55f8de22cd23c188571"
SRCREV_FORMAT = "vsomeip_googletest"

S = "${WORKDIR}/git"

DEPENDS = "boost"
DEPENDS:append = "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', ' systemd', '', d)}"

inherit cmake pkgconfig

EXTRA_OECMAKE = " \
    -DGTEST_ROOT=${WORKDIR}/googletest \
    -DDISABLE_DLT=ON \
    -DENABLE_SIGNAL_HANDLING=ON \
    -DINSTALL_LIB_DIR=${libdir} \
    -DINSTALL_CMAKE_DIR=${libdir}/cmake/vsomeip3 \
    -DDEFAULT_CONFIGURATION_FOLDER=${sysconfdir}/vsomeip \
"

do_install:append() {
    # Upstream installs example configs into /usr/etc/vsomeip.
    # Move them to the standard Yocto /etc/vsomeip location.
    if [ -d ${D}${prefix}/etc/vsomeip ]; then
        install -d ${D}${sysconfdir}/vsomeip
        cp -r ${D}${prefix}/etc/vsomeip/* ${D}${sysconfdir}/vsomeip/
        rm -rf ${D}${prefix}/etc
    fi

    # Remove any accidental build-directory artifacts from the target rootfs.
    if [ -d ${D}${B} ]; then
        rm -rf ${D}${B}
    fi
}

FILES:${PN} += " \
    ${sysconfdir}/vsomeip \
    ${libdir}/libvsomeip3.so.* \
    ${libdir}/libvsomeip3-cfg.so.* \
    ${libdir}/libvsomeip3-e2e.so.* \
    ${libdir}/libvsomeip3-sd.so.* \
"

FILES:${PN}-dev += " \
    ${libdir}/libvsomeip3.so \
    ${libdir}/libvsomeip3-cfg.so \
    ${libdir}/libvsomeip3-e2e.so \
    ${libdir}/libvsomeip3-sd.so \
    ${libdir}/cmake/vsomeip3 \
"
