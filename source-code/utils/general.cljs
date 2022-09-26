
(ns utils.general
  (:require
    [dom.api :as dom]))

(def default-scroll-config {:behavior "smooth"
                            :block    "start"
                            :inline   "center"})

(defn scroll-into [element-id & [config]]
  (let [element (dom/get-element-by-id element-id)]
    (.scrollIntoView element (clj->js (if config config default-scroll-config)))))
