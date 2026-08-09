SUMMARY = "AMIT Qt5 package group"
DESCRIPTION = "Qt5 runtime and development components for the AMIT graphical distribution."

LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN} = " \
    qtbase \
    qtbase-plugins \
    qtbase-tools \
"
