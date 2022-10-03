
(ns utils.api
  (:require
    [utils.scroll.effects]

    [utils.scroll.core :as scroll]
    [utils.html-parser :as html-parser]))

(def html->hiccup html-parser/html->hiccup)

(def scroll-into  scroll/scroll-into)
