SUMMARY = "Hello Bullet native application"
DESCRIPTION = "Simple native C application for testing the AMIT Bullet embedded Linux image."
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://hello-bullet.c"

S = "${WORKDIR}"

do_compile() {
    ${CC} ${CFLAGS} ${CPPFLAGS} \
        ${WORKDIR}/hello-bullet.c \
        -o hello-bullet \
        ${LDFLAGS}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 hello-bullet ${D}${bindir}/hello-bullet
}
