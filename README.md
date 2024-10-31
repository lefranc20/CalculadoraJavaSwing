Um exemplo de Calculador Simples em Java Swing. Instruções abaixo.

# Instruções de Compilação e Execução

## 1. Compilação
Navegue até o diretório onde os arquivos CalculadoraApp.java e CalculadoraInterface.java estão salvos e compile-os. Em ambos os sistemas (Windows e Linux), o comando é o mesmo.
``` bash
javac CalculadoraApp.java CalculadoraInterface.java
```

## 2. Execução
Após a compilação, execute a calculadora com o seguinte comando:
``` bash
java CalculadoraApp
```

# 1. Compilação para um arquivo executável distribuível
Compilando a Aplicação Java:
``` bash
javac -d out src\*.java
```

Agora vá para o diretório:
``` bash
cd out
```

Empacotando-o para um Arquivo JAR:
``` bash
jar --create --file ../CalculadoraApp.jar --main-class CalculadoraApp -C . .
```

## 2. Gerando o Executável com o jpackage:
No Linux:
``` bash
jpackage --name CalculadoraApp --input . --main-jar CalculadoraApp.jar --main-class CalculadoraApp --type app-image
```

No Windows, para um instalador:
``` bash
jpackage --name CalculadoraApp --input . --main-jar CalculadoraApp.jar --main-class CalculadoraApp --type exe
```
Ou um executável (o mesmo para o linux):
``` bash 
jpackage --name CalculadoraApp --input . --main-jar CalculadoraApp.jar --main-class CalculadoraApp --type app-image
```

*Nota: para o executável no Windows, o jpackage necessita do WiX Toolset para as ferramentas do **light.exe** e **candle.exe** *