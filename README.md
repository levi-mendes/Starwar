# Starwar

Para testar o aplicativo, faça o download e instale o apk que se encontra na aba "release".

- Para fazer a leitura de um QRCode clique no botão laranja da ActionBar para que o leitor seja acionado.
- Selecione o QRCode para que o aplicativo consiga efetuar a leitura.
- Após a leitura o serviço será consultado e os dados retornados serão salvos na base de dados do SQLite e adicionado a lista.
- Após clicar em um ítem da lista será aberta uma tela com os detalhes do ítem selecionado.


Site http://goqr.me foi usado para a criação dos QRCode's


QRCodes gerados para teste na pasta : https://github.com/levi-mendes/Starwar/tree/master/qrcodes



Bibliotecas usadas:

Butterknife - injeção de dependência de View's

Dagger 2    - injeção de dependência

Retrofit    - consulta web services

RXJava/RxAndroid

Glide       - carregamento e cache de imagens

QRCodeReaderView  - Leitora de QRCode's https://github.com/dlazaro66/QRCodeReaderView

Robolectric       - Teste unitário

Gson              - parse de objetos de/para conteúdo Json