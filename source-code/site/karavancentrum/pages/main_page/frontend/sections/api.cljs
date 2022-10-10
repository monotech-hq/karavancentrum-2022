
(ns site.karavancentrum.pages.main-page.frontend.sections.api
    (:require [site.karavancentrum.pages.main-page.frontend.sections.about-us :as about-us]
              [site.karavancentrum.pages.main-page.frontend.sections.brands   :as brands]
              [site.karavancentrum.pages.main-page.frontend.sections.contacts :as contacts]
              [site.karavancentrum.pages.main-page.frontend.sections.hero     :as hero]
              [site.karavancentrum.pages.main-page.frontend.sections.renting  :as renting]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(def about-us about-us/view)
(def brands   brands/view)
(def contacts contacts/view)
(def hero     hero/view)
(def renting  renting/view)
