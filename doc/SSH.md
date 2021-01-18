# SSH actions

+ [Back](../README.md)

## Algorithms

### Create new SSH key pair

+ `ssh-keygen -i keyFileName`

### Copy SSH key to remote server user

+ `ssh-copy-id -i generatedKeyFile user@host`

### Check SSH key

+ `ssh -i generatedKeyFile user@host`
