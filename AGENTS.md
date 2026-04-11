# AGENTS Guide for `BTL_Nhom3`

## Snapshot
- Android app with one module (`:app`), Java + XML UI, Gradle wrapper 9.3.1, AGP 9.1.0.
- Main package: `app/src/main/java/com/example/btl_nhom3`.
- Current working demo scope is menu/cart/profile; auth/order/admin are mostly scaffolds.

## Architecture map
- App starts at `MainActivity` + `activity_main.xml` (fragment host `frameMain` + bottom nav).
- Bottom-nav handling in `MainActivity`:
  - `nav_home` -> `HomeFragment`
  - `nav_menu` -> `MenuFragment`
  - `nav_profile` -> `ProfileFragment`
  - `nav_cart` -> starts `CartActivity`
- `nav_order` is defined in `app/src/main/res/menu/menu_bottom.xml` but currently has no handler in `MainActivity`.
- Package shape is feature-first: `feature_<name>/{ui,adapter,model,repository,viewmodel}` plus shared `core/{database,network,utils,base}`.

## Real data flows (today)
- Menu screen (`MenuFragment`) loads in-memory lists from `MenuRepository`, then filters locally by category + search text.
- Item detail handoff is Intent-based: `FoodAdapter` -> `FoodDetailActivity` using extras `name`, `price`, `image`, `desc`, `quantity`.
- Cart is not wired to menu detail yet: `CartActivity` reads hardcoded `CartRepository` data, computes total in activity, then opens `CheckoutActivity`.
- Logout in `ProfileFragment` clears `SharedPreferences("USER")` and relaunches `MainActivity` with clear-task flags.

## Integration status
- SQLite helper exists in `core/database/Database.java` with tables `users`, `categories`, `foods`, `cart`, `orders`, `order_items` and seed data.
- Repositories are still mock-data based; they do not call `Database` yet.
- `core/network/ApiService.java`, `core/utils/Constants.java`, and many auth/order/admin classes are placeholders.
- Dependencies are baseline AndroidX/Material only (`app/build.gradle`, `gradle/libs.versions.toml`); no Room/Retrofit/DI.

## Dev workflow (Windows PowerShell)
```powershell
.\gradlew.bat tasks --all
.\gradlew.bat app:assembleDebug
.\gradlew.bat app:testDebugUnitTest
.\gradlew.bat app:lintDebug
```

## Codebase conventions that matter
- Keep models local to each feature unless intentionally promoted to `core` (e.g., separate `Food` models already exist by feature).
- Keep naming style aligned with existing UI ids/fields: `rv*`, `txt*`, `btn*`, `edt*`.
- Navigation pattern is explicit fragments + `Intent` extras, not Navigation Component.
- Many UI strings are inline Vietnamese; keep style/language consistent unless doing a full i18n pass.
- For new work, choose one data strategy per flow (all mock repo or full SQLite-backed), avoid mixed partial wiring.


