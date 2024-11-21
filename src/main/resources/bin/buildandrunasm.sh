#!/bin/zsh

cd ..

as -o program.o program.asm

ld -o programasm program.o

./programasm
