SUMMARY = "AMIT Bullet lightweight observability service"
DESCRIPTION = "Lightweight monitoring service for CPU load, memory, disk, uptime and network statistics."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://bullet-observer \
    file://bullet-observer.service \
    file://bullet-observer.init \
"

S = "${WORKDIR}"

inherit systemd update-rc.d

SYSTEMD_SERVICE:${PN} = "bullet-observer.service"
SYSTEMD_AUTO_ENABLE = "enable"

INITSCRIPT_NAME = "bullet-observer"
INITSCRIPT_PARAMS = "defaults 95 05"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/bullet-observer \
        ${D}${bindir}/bullet-observer

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/bullet-observer.service \
        ${D}${systemd_system_unitdir}/bullet-observer.service

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/bullet-observer.init \
        ${D}${sysconfdir}/init.d/bullet-observer
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/bullet-observer.service \
    ${sysconfdir}/init.d/bullet-observer \
"
