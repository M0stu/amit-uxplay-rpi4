SUMMARY = "AMIT observability package group"
DESCRIPTION = "Monitoring and observability packages for the AMIT Bullet project."

LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN} = " \
    bullet-observer \
"
