
(ns site.karavancentrum.modules.api
  (:require
   [site.karavancentrum.modules.footer :as footer]
   [site.karavancentrum.modules.navbar :as navbar]
   [site.karavancentrum.modules.sidebar.views :as sidebar]))

(def sidebar sidebar/view)

(def navbar navbar/view)

(def footer footer/view)
