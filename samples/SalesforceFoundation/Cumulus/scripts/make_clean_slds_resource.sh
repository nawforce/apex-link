#!/usr/bin/env bash

clean_slds_files() {
    rm -fv $1/assets/fonts/License-for-font.txt
    rm -fv $1/assets/fonts/*.ttf
    rm -fv $1/assets/fonts/webfonts/*.eot
    rm -fv $1/assets/fonts/webfonts/*.svg
    rm -fv $1/assets/fonts/webfonts/SalesforceSans-Thin.woff
    rm -fv $1/assets/fonts/webfonts/SalesforceSans-Thin.woff2
    rm -fv $1/assets/fonts/webfonts/SalesforceSans-ThinItalic.woff
    rm -fv $1/assets/fonts/webfonts/SalesforceSans-ThinItalic.woff2
    rm -fv $1/assets/icons/action/*.png
    rm -fv $1/assets/icons/action-sprite/symbols.html
    rm -fv $1/assets/icons/custom/*.png
    rm -fv $1/assets/icons/custom-sprite/symbols.html
    rm -fv $1/assets/icons/doctype/*.png
    rm -fv $1/assets/icons/doctype-sprite/symbols.html
    rm -fv $1/assets/icons/standard/*.png
    rm -fv $1/assets/icons/standard-sprite/symbols.html
    rm -fv $1/assets/icons/utility/*.png
    rm -fv $1/assets/icons/utility-sprite/symbols.html
    rm -fv $1/assets/icons/README
    rm -fv $1/assets/icons/License-for-icons.txt
    rm -fv $1/assets/images/License-for-images.txt
    rm -fv $1/assets/styles/salesforce-lightning-design-system-ltng.css
    rm -fv $1/assets/styles/salesforce-lightning-design-system-scoped.css
    rm -fv $1/assets/styles/salesforce-lightning-design-system-vf.css
    rm -fv $1/assets/styles/salesforce-lightning-design-system.css
    rm -fv $1/README.md
    rm -rfv $1/scss
    rm -rfv $1/swatches
}

SLDS_TMPDIR=`mktemp -d -t sldsclean.XXXXXXXX`

unzip -d $SLDS_TMPDIR/0_12_2 salesforce-lightning-design-system-0.12.2.zip
unzip -d $SLDS_TMPDIR/1_0_3 salesforce-lightning-design-system-1.0.3.zip

clean_slds_files $SLDS_TMPDIR/0_12_2
clean_slds_files $SLDS_TMPDIR/1_0_3
rm -fv $SLDS_TMPDIR/1_0_3/package.json


SLDS_OUTDIR=`pwd`
pushd $SLDS_TMPDIR
zip -r $SLDS_OUTDIR/SLDS.resource 0_12_2 1_0_3
popd
