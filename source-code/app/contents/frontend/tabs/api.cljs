
(ns app.contents.frontend.tabs.api
  (:require
    [app.contents.frontend.tabs.brands.core :as brands]
    [app.contents.frontend.tabs.about-us :as about-us]
    [app.contents.frontend.tabs.main-page :as main-page]
    [app.contents.frontend.tabs.rent-information :as rent-information]))


(def brands brands/view)

(def about-us about-us/view)

(def main-page main-page/view)

(def rent-information rent-information/view)
