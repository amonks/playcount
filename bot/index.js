'use strict'

let rp = require('request-promise')
let baseurl = 'http://count-bot-game.herokuapp.com'

function parseRes (r) {
  return JSON.parse(r).count
}

rp.get(`${baseurl}/stats`)
.then(function (res) {
  console.log(res)
  let count = parseRes(res)
  console.log(count)

  setInterval(function () {
    let url = `${baseurl}/play/monksbot/${count + 1}`
    console.log(url)
    rp.get(url)
    .then(function (res) {
      console.log(res)
      count = parseRes(res)
    })
  }, 2000)
})

