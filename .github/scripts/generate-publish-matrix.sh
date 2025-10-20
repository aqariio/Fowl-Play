#!/bin/bash
# Credit goes to the Friends & Foes mod by Faboslav

platforms=$1

IFS=',' read -r -a platforms_array <<< "${platforms//[\[\]\']/}"
matrix_content="{\"include\":["
enabled_platforms=$(awk -F= '/enabled_platforms/{print $2}' gradle.properties | tr -d ' ')

for platform in $(echo $enabled_platforms | tr ',' ' '); do
    if [[ " ${platforms_array[@]} " =~ " ${platform} " ]]; then
  	    if [[ "$platform" == "fabric" ]]; then
        	loaders="\"fabric\",\"quilt\""
        else
        	loaders="\"$platform\""
        fi

        matrix_entry="{\"platform\":\"$platform\",\"loaders\":[$loaders]},"
        matrix_content+="$matrix_entry"
    fi
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "::set-output name=matrix::$matrix_content"