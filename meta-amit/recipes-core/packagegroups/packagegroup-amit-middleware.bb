SUMMARY = "AMIT middleware package group"
DESCRIPTION = "Middleware components used by the AMIT Bullet project."

LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN} = " \
    vsomeip \
"
