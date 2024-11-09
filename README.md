# Todo: A Simple To-do aplication

## install

I recomend using asdf tool, see documentations for it see [asdf documentation](https://asdf-vm.com/guide/getting-started.html)

### Install Java (21)

Add Java Plugin
```bash
asdf plugin-add java
```


Install java version.
```bash
asdf install java openjdk-21
```

#### Optional

Set this one for all system:
```bash
asdf global java openjdk-21
```

### Install Maven (3.9.9)

Add Maven Plugin
```bash
asdf plugin-add maven
```

Install java version.
```bash
asdf install maven 3.9.9
```


#### Optional


Set this one for all system:
```bash
asdf global maven 3.9.9
```
### Compile and Install

```
mvn compile install
```

### Run

```
java -jar target\Todo*.jar --help
```
