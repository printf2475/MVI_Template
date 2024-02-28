# MVI_Template
Compose + MVI + Clean Architecture + Ktor

- 현재 사용중인 컨벤션 및 구조 입니다
- 추후 새로운 앱을 개발할 때 빠르게 개발하기 위한 템플릿 입니다.
- 편의상 Domain Layer 와 Ui Layer 사이에 Mapper를 사용하지 않는 방식을 사용하고있습니다.
- local.properties에 BaseURL을 사용하도록 하여야 하지만 테스트를 위해 gradle파일에 String으로 추가하였습니다.

## 패키지 구조
- Data
  - Api
  - Mapper
  - Model
  - Repository
    - Local
    - Remote
- DI
- Domain
  - Model
  - Repository
  - UseCase
  - Util
- UI
  - Component
  - Main(Home)
  - Theme
