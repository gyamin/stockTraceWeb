#!/bin/bash
execDir=$(cd $(dirname $0); pwd)    # webapp/assets/bin
source ${execDir}/_common.sh

# compile typescript files
if [ "${1}" = "watch" ]; then
    tsc -w --rootDir ${jsSrcDir} --outDir ${jsOutDir} &
    ${baseDir}/node_modules/watch/cli.js "rsync -av --delete --include='*/' --include='*.html' --exclude='*' ${jsSrcDir}/ ${jsOutDir}/" ${jsSrcDir}
else
    tsc --rootDir ${jsSrcDir} --outDir ${jsOutDir}
    rsync -av --delete --include='*/' --include='*.html' --exclude='*' ${jsSrcDir}/ ${jsOutDir}/
fi