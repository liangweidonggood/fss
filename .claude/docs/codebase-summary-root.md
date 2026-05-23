# FSS 根配置总结

## 一、Monorepo 配置

### pnpm-workspace.yaml
```
packages:
  - "apps/*"
  - "apps/web/*"
  - "apps/desktop/*"
  - "packages/*"
```
仅管理前端代码，后端目录 (`backend/`) 不加入 workspace。

### package.json (根)
- **脚本**: `lint`, `lint:fix`, `format`, `build`, `turbo:lint`
- **引擎**: Node >=22.21.1, pnpm >=10.26.0
- **devDependencies**: turbo 2.7, prettier 3.7, eslint 9.39, @typescript-eslint/* 8.48, stylelint 16.25, husky 9.1, lint-staged 16.2, commitlint 19.8
- **lint-staged**: JS/TS/TSX/JSX → `eslint --fix`, CSS/Less → `stylelint --fix`
- **commitlint**: `npx commitlint --edit $1` (从 commit-msg hook 调用)

### turbo.json
- tasks: build, lint, format, typecheck, clean
- **lint task**: 声明 outputs，建议 `dependsOn: ["^build"]`

## 二、代码质量工具链

### ESLint (eslint.config.js)
- 扁平配置，导入 `global/config/tsconfig.base.json`
- extends: `@typescript-eslint/*`, `plugin:@typescript-eslint/recommended`
- 规则：`@typescript-eslint/no-unused-vars: warn` (前缀 `_` 忽略)
- 忽略 dist, node_modules

### Prettier (.prettierrc.cjs)
- 单引号，分号，行宽 120
- 缩进 2 空格，尾逗号 all
- 换行: lf
- 忽略规则: `*.cjs` 文件

### Stylelint (.stylelintrc.cjs)
- 标准 CSS 检查
- 忽略: node_modules, dist

## 三、Git Hooks (Husky)

### pre-commit
```bash
npx lint-staged
```
对暂存文件运行 ESLint + Stylelint 自动修复。

### commit-msg
```bash
npx commitlint --edit $1
```
基于 `.commitlintrc.cjs` 校验提交消息格式。

## 四、.gitignore
忽略 node_modules, dist, .turbo, .env*, .DS_Store, *.log, tmp, *.local, .idea

## 五、AGENTS.md 层级
- 根 `AGENTS.md`: 项目级 AI 助手指引
- `apps/AGENTS.md`: 前端应用层指引
- `backend/AGENTS.md`: 后端模块指引
- `packages/AGENTS.md`: 共享包开发指引

## 六、CLAUDE.md
项目级配置定义于 `.claude/CLAUDE.md`：技术栈版本、开发命令、架构描述、Git 提交规范。
