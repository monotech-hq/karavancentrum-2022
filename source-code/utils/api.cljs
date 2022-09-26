
(ns utils.api
  (:require
    [utils.general :as general]
    [utils.html-parser :as html-parser]))

(def html->hiccup html-parser/html->hiccup)

(def scroll-into  general/scroll-into)
