const express = require("express");
const app = express();
const port = 3000;

const books = [
  {
    "id": 1,
    "title": "",
    "author": "",
    "year": ""
  },
  {
    "id": 2,
    "title": "",
    "author": "",
    "year": ""
  },
  {
    "id": 3,
    "title": "",
    "author": "",
    "year": ""
  },
  {
    "id": 4,
    "title": "",
    "author": "",
    "year": ""
  },
  {
    "id": 5,
    "title": "",
    "author": "",
    "year": ""
  },
  {
    "id": 6,
    "title": "",
    "author": "",
    "year": ""
  },
  {
    "id": 7,
    "title": "",
    "author": "",
    "year": ""
  },
  {
    "id": 8,
    "title": "",
    "author": "",
    "year": ""
  },
]

app.get("/", (req, res) => res.send({
  "message": "Book Returner 8000"
}));

app.get("/books", (req, res) => res.send({
  "books": books
}));

app.get("/book/:bookId/detail", (req, res) => {
  const bookId = req.params.bookId;
  if (bookId > 5) {
    setTimeout(() => {
      res.send({
        "details": {
          "id": bookId,
          "detail": "This is a book synopsis!"
        }
      })
    }, Math.floor(Math.random() * 5000) + 1000);
  }
});

app.listen(port);