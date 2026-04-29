# shredder
<img width="355" height="289" alt="image" src="https://github.com/user-attachments/assets/ae8e4eeb-0dcf-450b-83d7-ff5846d5a575" />

> Secure file deletion utility - overwrites file contents with random data before removing it from disk.

![Java](https://img.shields.io/badge/Java-17%2B-blue)

## How it works

1. Validates the target is a regular file
2. Opens it with `RandomAccessFile` in `rw` mode
3. Overwrites the entire content **3 times** with `SecureRandom` bytes
4. Forces each pass to disk via `getFD().sync()`
5. Renames the file to a random `UUID.tmp`
6. Deletes the renamed file

## Usage

```bash
javac *.java
java Main
```

## ⚠️ Note on SSDs

On SSDs, wear leveling may prevent true overwrite of original sectors.
Journaling filesystems (NTFS, ext4, btrfs) may retain independent copies.
For guaranteed erasure on SSD - use full-disk encryption from day one,
then wiping the encryption key is sufficient.

## Built with

Pure Java stdlib - `SecureRandom`, `RandomAccessFile`, `java.nio.file`, `UUID`.
No dependencies, no frameworks.
