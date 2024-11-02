# Todo: A Simple To-do aplication

## install

I recomend using asdf tool, see documentations for it see [asdf documentation](https://asdf-vm.com/guide/getting-started.html)

### Install Java (21)

```bash
asdf plugin-add java
```
Show all version for Java:
```bash
asdf list-all java
```
Choose one:
```bash
asdf install java openjdk-21
```

### Optional (this directory alread has a asdf .tool-versions file)
Set this one for all system:
```bash
asdf global java openjdk-21
```
Or set only for this diretory (recomended):

```bash
asdf local java openjdk-21
```
