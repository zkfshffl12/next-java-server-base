#!/bin/bash

IMAGE_NAME=tamibang/next-server
TAG=latest

# 빌드
docker build -t $IMAGE_NAME:$TAG .

# 푸시
docker push $IMAGE_NAME:$TAG