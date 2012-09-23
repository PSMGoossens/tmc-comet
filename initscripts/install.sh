#!/bin/bash -e
THISDIR=`dirname "$0"`
BASEDIR=`dirname "$THISDIR"`
TEMPLATE="$THISDIR/template.txt"
SCRIPT_NAME=tmc-comet
TARGET="/etc/init.d/$SCRIPT_NAME"
RUN_AS=`stat -c '%U' "$BASEDIR"`

which nc > /dev/null || (echo "Error: 'nc' command missing! Please install netcat."; exit 1)

echo "Installing into $TARGET."
echo "Configuring it to run as $RUN_AS (the current owner of the directory)."
cat "$TEMPLATE" |
  sed "s@__BASEDIR__@$BASEDIR@" |
  sed "s@__NAME__@$SCRIPT_NAME@" |
  sed "s@__USER__@$RUN_AS@" > $TARGET
chmod a+x "$TARGET"

echo "Setting to start/stop by default"
update-rc.d "$SCRIPT_NAME" defaults 90 10
