SUMMARY = "AMIT base package group"
DESCRIPTION = "Common packages required by AMIT Raspberry Pi images."

LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN} = " \
    hello-bullet \
    nano \
    openssh \
    wpa-supplicant \
    alsa-utils \
    avahi-daemon \
    avahi-utils \
"
