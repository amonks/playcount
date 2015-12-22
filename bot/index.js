'use strict'

let rp = require('request-promise')

function parseRes (r) {
  return JSON.parse(r).count
}

rp.get('http://localhost:3000/stats')
.then(function (res) {
  console.log(res)
  let count = parseRes(res)
  console.log(count)

  setInterval(function () {
    let url = `http://localhost:3000/play/monksbot/${count + 1}`
    console.log(url)
    rp.get(url)
    .then(function (res) {
      console.log(res)
      count = parseRes(res)
    })
  }, 2000)
})

