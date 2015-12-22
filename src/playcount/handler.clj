(ns playcount.handler
  (:use markdown.core)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defonce state (atom {:count 0
                      :scores {}}))

(defn str->int [str] (if (re-matches (re-pattern "\\d+") str) (read-string str)))
(defn stats [] (json/write-str @state))

(defn index [] (slurp "index.md"))
(def index-memo (memoize index))

(defn leaderboard []
  (let [scores (sorted-map (:scores @state))]
    (clojure.string/join "\n"
                         (map
                           (fn [pair]
                             (str "* *" (first pair) "*: " (second pair)))
                           (seq (:scores @state))))))

(defroutes app-routes
  (GET "/" [] (md-to-html-string (str (index) (leaderboard))))
  (GET "/stats" [] (stats))
  (GET "/scores" [] (md-to-html-string (leaderboard)))
  (GET "/stats.json" [] (stats))
  (GET "/play/:player/:number" [player number]
       (let [number (str->int number)
             old-count (:count @state)
             new-count (inc old-count)
             result (if (= number new-count)
                      "success"
                      "failure")
             old-score (or (get-in @state [:scores player]) 0)
             new-score (if (= result "success")
                         (inc old-score)
                         (dec old-score))]
         (println "old score " old-score " new score " new-score)
         (swap! state update-in [:scores player] (fn [x] new-score))
         (swap! state assoc :count new-count)
         (json/write-str (merge {:play number
                                 :result result
                                 :score new-score
                                 :player player}
                                 @state))))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
