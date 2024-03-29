<h1 align="center">🌱 The Seed</h1>

<p align="center">
    <br>
    <i>
        "The little seed I planted found purchase in
        distant networks, where it sprouts its own leaves and branches.
    </i>
</p>
<h6 align="right">
    Kayaba Akihiko, in <a href="https://en.wikipedia.org/wiki/Sword_Art_Online">Sword Art Online</a> by <a href="https://en.wikipedia.org/wiki/Reki_Kawahara">Kawahara Reki</a>.
</h6>
<br>
<br>

## 📑 Mô tả

Đây là một dự án có những thành phần cơ bản để làm một MMORPG cơ bản, như Sword Art Online.
Dự án có những item và quái vật cơ bản trong tiểu thuyết gốc. Để tạo một MMORPG từ dự án này,
hãy fork repo.

## 👀 Sử dụng

### Cho mọi người 🙌

Tải build mới nhất xuống từ tab Actions. Từ đó kéo xuống phần Artifacts rồi tải xuống file zip. Giải nén và đặt file jar
vào trong folder `plugins` của server.

### Cho dev (cần ít nhất 200🧠 IQ)

* Clone hoặc tải xuống 1 snapshot của repo. Mở dự án bằng IDE của bạn: Visual Studio (Code) (wtf), Intellij IDEA (ok),
  Eclipse, (n)vim (👌), ...
* Compile bằng Gradle:

```sh
$ ./gradlew build
```

* Paste file `.jar` trong folder `libs/` vào trong folder `plugins/` của server.

## 💻 Cống hiến & Giúp đỡ

Nếu bạn giúp đỡ viết plugin, dù chỉ là fix typo trong file này, thì sự giúp đỡ đó luôn được
hoan nghênh. Dự án này cần có người viết code, người tìm lỗi, người viết doc, và có thể là một
nhà tài trợ.

Khi bạn cống hiến code, có thể commit của bạn sẽ bị rollback, issue bị đóng hoặc label "won't fix". Chúng tôi sẽ đưa cho
bạn một lí do chính đáng, và đừng buồn 🙌: không phải code bạn rác hay bạn không có chỗ trong dự án này, có thể code của
bạn không đúng hướng với hướng đi của dự
án. Bạn hoàn toàn có thể thử lại (trừ khi bạn đang spam thì đừng thử lại nữa :))) ).

Bạn cũng nên theo codestyle của dự án. Cơ bản là:

* Tab = 4 spaces; chúng tôi không viết Linux kernel hay theo Google codestyle ở đây.
* Tuân theo PEP8 của Python, dù trong Java (wtf).
* Docstring như điên, kể cả những method và class đơn giản nhất.
* Tóm lại là để Intellij autoformat cho.
* Nhớ add copyright vào đầu mỗi file.

## 📖 Ghi công & Bản quyền.

Dự án này được đặt dưới giấy phép Apache, bản 2.0. Các tác giả của phần mềm này cho phép bạn sử dụng cho mục đích cá
nhân hoặc thương mại, tích hợp phần mềm này vào một phần mềm khác; miễn là bạn đính kèm một bản sao giấy phép Apache
cùng với danh sách thay đổi của bạn.

## FAQ

| Q: Hỏi                                                        | A: Đáp                                                                     |
|---------------------------------------------------------------|----------------------------------------------------------------------------|
| Tôi có thể sử dụng nó ở trong server riêng được không?        | Được. Không cần credit luôn.                                               |
| Tôi tìm thấy lỗi trong dự án.                                 | Tạo một issue mới trong tab issues. Chúng tôi sẽ xem xét và giải quyết nó. |
| Tao có skill Ăn Cắp 1000. Tao ăn cắp cái repo này được không? | Được. Nó mã nguồn mở mà.                                                   |
| Tại sao lại có dự án này?                                     | Vì thằng owner nó nghiện SAO.                                              |
| Code đẹp quá!                                                 | ừ ok, thằng owner khi viết ra dòng này đã phải phì cười.                   |
| Code như cc.                                                  | ok. anh bạn đã nói thế thì fix giúp đi.                                    |
| ??                                                            | !!                                                                         |
| `:middle_finger:`                                             | `:monkey: :dash:`                                                          |

<br><br><p align="center">Made with ❤️ by SpikeBonjour.</p>
