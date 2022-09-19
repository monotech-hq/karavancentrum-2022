
(ns site.pages.main-page.frontend.views
  (:require
    [x.app-core.api :as a]
    [site.pages.main-page.frontend.sections.api :as sections]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn main-page []
  [:main#main-page--content
   [sections/section-1]
   [sections/section-2]])

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view [_]
  [main-page])
