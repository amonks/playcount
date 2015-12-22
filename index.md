# NET.COUNT

This is a game

You *could* play it in your browser, but you're encouraged to write a program (in a language of your choice) and play with that.

## INSTRUCTIONS

Play happens by making requests at this server.

Here are the endpoints:

* [`/stats`](/stats) returns a json object with the current `count`, plus a leaderboard.
* [`/play/[yourname]/[yourplay]`](/play/alice/100) makes a play. It returns a json object with your play, the result of that play, your name, the current `count`, plus a leaderboard.

The game is very simple. Every time someone plays, the `count` goes up by 1. If the number you play *is the same* as the new `count`, your play is a success and you gain a point. If the number you play *is not* the new `count`, your play is a fail and you lose a point.

That's all.

* * *

## SCORES
