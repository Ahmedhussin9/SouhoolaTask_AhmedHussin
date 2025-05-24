# 📱 Souhoola News App – Android Developer Task

A modern, Compose-based Android news app built for the Souhoola developer task. Designed with a clean architecture and intuitive UI to showcase skills in Jetpack Compose, MVVM, Paging, and more.

---

## ✨ Features

- Paginated list of news articles from NewsAPI
- Jetpack Compose UI using Material 3
- Sort by: Latest, Relevant, or Popular
- Swipe-to-refresh
- Search bar with keyboard "Done" handling
- Article detail screen with:
  - Full content display
  - "Read More" → opens in browser
  - "Share" → opens Android’s share sheet
- Scroll-to-top FAB
- Error and empty states

---

## 🔐 API Key Note (Important)

To simplify setup for reviewers, the API key is embedded directly in the app.
---

🔍 About the Search Behavior
Search is only triggered when the keyboard "Done" action is pressed.

Why?
NewsAPI has a limited number of free requests per day. To prevent excessive API calls on every character change (which could quickly exhaust the quota), I implemented manual search submission only when the user confirms the query.
---
📸 Screenshots
![WhatsApp Image 2025-05-24 at 15 29 25_65b73c99](https://github.com/user-attachments/assets/ef31c17c-b1bd-4be8-b533-ca673de75354)
![WhatsApp Image 2025-05-24 at 15 29 34_d0bf8524](https://github.com/user-attachments/assets/ec188d5f-37e5-49b0-bb58-a0a4a778c1d7)
![WhatsApp Image 2025-05-24 at 15 29 37_7b80e563](https://github.com/user-attachments/assets/5f80715d-626b-4680-8eb6-6445a11bf924)
![WhatsApp Image 2025-05-24 at 15 29 37_925916de](https://github.com/user-attachments/assets/e0ab2d9a-17a4-48ed-ad75-7579a94dc203)
---
🧑‍💻 Tech Stack
- Jetpack Compose – Declarative UI framework

- Paging 3 – Efficient data loading

- Retrofit – Network communication

- Hilt – Dependency injection

- Coil – Image loading

- Navigation Compose – Seamless screen transitions

- StateFlow + ViewModel – Reactive state handling

---
🗂 Project Highlights
- MVVM + Clean Architecture structure

- Scroll-to-top button after scrolling

- Consistent Material 3 design system

- Edge case handling (empty results, API errors)

- Fully modular & testable setup

--- 
🧪 Testing Notes
- Confirmed pagination and refresh behavior

- Verified sorting and search work as expected

- Tested article open and share flows

- Error handling tested with empty queries and no internet
---
📌 Author
Ahmed Hussin,
Android Developer
