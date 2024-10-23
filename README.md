
# CP3-Mobile

Este é o aplicativo móvel **CP3-Mobile**, que faz parte de um sistema de gerenciamento de relógios. O aplicativo permite que os usuários criem, editem e visualizem informações sobre diferentes modelos de relógios, incluindo detalhes como marca, preço, data de lançamento e imagem.

# Projeto Feito por : 
- Gabriel Lima RM99743
- Murilo Matos RM9

## 📱 Funcionalidades

- Criar novos relógios com nome, descrição, marca, preço, data de lançamento e imagem.
- Editar informações de relógios já cadastrados.
- Visualizar a lista de relógios cadastrados.
- Adicionar imagens a partir da galeria do dispositivo.

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **Arquitetura**: MVC (Model-View-Controller)
- **Banco de Dados**: SQLite
- **Bibliotecas**:
  - [Glide](https://github.com/bumptech/glide): Para carregamento e exibição de imagens.
  - [AndroidX](https://developer.android.com/jetpack/androidx): Suporte a componentes e recursos Android.
  - [Material Design](https://material.io/develop/android): Interface baseada nas diretrizes do Material Design.

## 🔧 Instalação e Configuração

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/gabriellima-wav/CP3-Mobile.git
   cd CP3-Mobile
   ```

2. **Abra o projeto no Android Studio**:

   - Abra o Android Studio.
   - Clique em `File -> Open` e selecione a pasta do projeto.
   - Aguarde o Android Studio sincronizar o projeto e baixar as dependências.

3. **Executar o aplicativo**:

   - Conecte um dispositivo Android ou inicie um emulador.
   - Clique no botão `Run` ou use o atalho `Shift + F10`.

4. **Permissões**:
   - O aplicativo solicita permissão para acessar a galeria de imagens do dispositivo. Certifique-se de conceder a permissão quando solicitado, ou vá em **Configurações do dispositivo** > **Aplicativos** > **CP3-Mobile** > **Permissões** e habilite manualmente.

## 📂 Estrutura do Projeto

O projeto está organizado da seguinte forma:

```bash
CP3-Mobile/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/cp3_mobile/
│   │   │   │   ├── database/              # Lógica de banco de dados (SQLite)
│   │   │   │   ├── models/                # Definição da entidade Watch
│   │   │   │   ├── ui/                    # Atividades e componentes de UI
│   │   │   │   ├── WatchFormActivity.kt   # Formulário para adicionar/editar relógios
│   │   │   │   ├── WatchListActivity.kt   # Exibição da lista de relógios
│   │   │   ├── res/
│   │   │   │   ├── layout/                # XML de layouts de atividades
│   │   │   │   ├── drawable/              # Recursos de imagem e ícones
├── build.gradle                            # Configurações de build do Gradle
```

### Principais Componentes

- **WatchFormActivity**: Atividade responsável por criar ou editar relógios. Permite que o usuário selecione uma imagem da galeria e preencha os dados do relógio.
- **WatchListActivity**: Exibe a lista de relógios cadastrados no banco de dados.
- **DatabaseHelper**: Classe auxiliar para manipulação do banco de dados SQLite, onde os dados dos relógios são armazenados.

## 📸 Adicionando Imagens

O aplicativo permite que os usuários adicionem uma imagem para cada relógio. As imagens são selecionadas diretamente da galeria do dispositivo e exibidas na interface de usuário usando a biblioteca **Glide**. Certifique-se de conceder permissão de leitura de armazenamento ao aplicativo para que o carregamento funcione corretamente.

## 📑 Licença

Este projeto está sob a licença [MIT](https://opensource.org/licenses/MIT). Sinta-se à vontade para modificar e distribuir o código conforme necessário.

## ✨ Contribuição

Contribuições são bem-vindas! Para contribuir com o projeto:

1. Faça um fork do repositório.
2. Crie uma branch para a sua feature (`git checkout -b minha-feature`).
3. Faça o commit das suas alterações (`git commit -m 'Adiciona minha feature'`).
4. Faça push para a branch (`git push origin minha-feature`).
5. Abra um Pull Request.
