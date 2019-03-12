# leadKeyFile
How to import private key(RSA) in java

## How To
1. create private key in OpenSSL etc...

`openssl genrsa -out private_key.pem 1024`

This private key is created in PEM and PKCS#1 format.

2. transform to DER and PKCS#8 fromat.

`openssl pkcs8 -in private_key.pem -topk8 -nocrypt -outform DER -out private_key.pk8`

# env
Java 10.0.2

