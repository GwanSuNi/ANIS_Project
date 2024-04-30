# Commit Message Convention
## 커밋 메세지 구조
`Type / Body / Footer `
> Feat: Add member api
### 예시
```html
Feat: "Add login API"        // 타입: 제목

로그인 API 개발               // 본문

Resolves: #123              // 꼬리말 => 이슈 123을 해결했으며,
Ref: #456                                 이슈 456 를 참고해야하며,
Related to: #48, #45         현재 커밋에서 아직 이슈 48 과 45 가 해결되지 않았다.
```
---
### 1. Type
- Feat : 새로운 기능을 추가하는 경우
- Fix : 버그를 고친경우
- Docs : 문서를 수정한 경우
- Style : 코드 포맷 변경, 세미콜론 누락, 코드 수정이 없는경우
- Refactor : 코드 리펙토링
- Test : 테스트 코드. 리펙토링 테스트 코드를 추가했을 때
- Chore : 빌드 업무 수정, 패키지 매니저 수정
- Design : CSS 등 사용자가 UI 디자인을 변경했을 때
- Rename : 파일명(or 폴더명) 을 수정한 경우
- Remove : 코드(파일) 의 삭제가 있을 때. "Clean", "Eliminate" 를 사용하기도 함
- Add : 코드나 테스트, 예제, 문서등의 추가 생성이 있는경우
- Improve : 향상이 있는 경우. 호환성, 검증 기능, 접근성 등이 될수 있습니다.
- Implement : 코드가 추가된 정도보다 더 주목할만한 구현체를 완성시켰을 때
- Move : 코드의 이동이 있는경우
- Update : 계정이나 버전 업데이트가 있을 때 사용. 주로 코드보다는 문서나, 리소스, 라이브러리등에 사용합니다.
- Comment : 필요한 주석 추가 및 변경

### 제목
1. 제목은 대문자를 넘기지 않고, 대문자로 작성, 마침표 없이
2. 과거형을 사용하지 않고 명령조로 시작
---
### 2. Body
1. 선택사항
2. 부연설명이 필요할 때, **무엇을 변경 했는지** 또는 **왜 변경 했는지** 설명
---
### 3. Footer
1. 선택사항
2. issue tracker id를 작성할 때 사용
3. 형식 : `유형: #이슈 번호`

- Fixes : 이슈 수정중 (아직 해결되지 않은 경우)
- Resolves : 이슈를 해결했을 때 사용
- Ref : 참고할 이슈가 있을 때 사용
- Related to : 해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)
  ex) Fixes: #45 Related to: #34, #23
---
### 참고 자료
커밋 컨벤션 : <https://velog.io/@msung99/Git-Commit-Message-Convension> <br>
마크다운 : <https://www.heropy.dev/p/B74sNE>
