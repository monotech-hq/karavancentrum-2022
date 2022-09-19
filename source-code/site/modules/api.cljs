
(ns site.modules.api
  (:require
   [site.modules.footer :as footer]
   [site.modules.navbar :as navbar]
   [site.modules.sidebar.views :as sidebar]))

(def sidebar sidebar/view)

(def navbar navbar/view)

(def footer footer/view)
