(ns aoc1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [xtdb.api :as xt]
            [xtdb.node :as node]))

(def node (node/start-node {}))

(def lines (-> (slurp (io/resource "input.txt"))
               str/split-lines))

(doseq [tx (->> lines
                (map-indexed #(xt/put :docs {:xt/id %1 :line %2}))
                (partition-all 1024))]
  (xt/submit-tx node tx))

(xt/q node '(-> (from :docs [line])
                (with {:first-pos (position "0123456789" line)})))
