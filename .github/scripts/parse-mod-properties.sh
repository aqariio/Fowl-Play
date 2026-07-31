#!/bin/bash
set -euo pipefail

# parses stonecutter.properties.toml and exports its values as GitHub outputs
# requires Python 3.11+

parse_properties_file() {
    local file="$1"

    python3 - "$file" "$GITHUB_OUTPUT" <<'PY'
import json
import re
import sys
import tomllib
from pathlib import Path

input_file = Path(sys.argv[1])
github_output = Path(sys.argv[2])

with input_file.open("rb") as file:
    properties = tomllib.load(file)


def normalize_key(parts: tuple[str, ...]) -> str:
    key = "_".join(parts).upper()
    key = re.sub(r"[^A-Z0-9]+", "_", key).strip("_")

    # Environment/output names should not begin with a number.
    if key and key[0].isdigit():
        key = "_" + key

    return key


def format_value(value) -> str:
    if isinstance(value, bool):
        return str(value).lower()

    if isinstance(value, list):
        # Convenient for GitHub Actions and shell commands.
        return " ".join(str(item) for item in value)

    if isinstance(value, dict):
        return json.dumps(value, separators=(",", ":"))

    return str(value)


def flatten(value, path=()):
    if isinstance(value, dict):
        for key, child in value.items():
            yield from flatten(child, path + (str(key),))
    else:
        yield normalize_key(path), format_value(value)


outputs = list(flatten(properties))

with github_output.open("a", encoding="utf-8") as output_file:
    for key, value in outputs:
        print(f"{key}={value}")
        output_file.write(f"{key}={value}\n")
PY
}

parse_properties_file "stonecutter.properties.toml"