
(ns site.frontend.pages.api
  (:require
   [site.frontend.pages.main-page.core :as main-page]))

;; -----------------------------------------------------------------------------
;; ---- Endpoints ----

(def main-page  main-page/view)
