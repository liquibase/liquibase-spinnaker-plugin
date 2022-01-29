#!/bin/bash

set -ex

if [[ -z "$LB_HOST_NAME" ]]; then
  echo "Set the LB_HOST_NAME env variable."
  exit 1
fi

if [[ -z "$LB_HOST_IP" ]]; then
  echo "Set the LB_HOST_IP env variable."
  exit 1
fi

if [[ -z "$CHANGELOG_REPO" ]]; then
  echo "Set the CHANGELOG_REPO env variable."
  exit 1
fi

if [[ -z "$CHANGELOG_BRANCH" ]]; then
  echo "Set the CHANGELOG_BRANCH env variable."
  exit 1
fi

echo "$LB_HOST_IP $LB_HOST_NAME" >> /etc/hosts 2>&1
mkdir /workspace
chown liquibase:liquibase /workspace
sudo -u liquibase git clone --quiet --single-branch --branch $CHANGELOG_BRANCH $CHANGELOG_REPO /workspace
cd /workspace

read -a lbcmd <<< $1
/docker-entrypoint.sh "${lbcmd[@]}"