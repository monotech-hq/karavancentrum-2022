
(ns site.components.frontend.api
    (:require [site.components.frontend.credits.views       :as credits.views]
              [site.components.frontend.scroll-sensor.views :as scroll-sensor.views]))
 
;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; site.components.frontend.credits.views
(def copyright-label  credits.views/copyright-label)
(def mt-logo          credits.views/mt-logo)
(def created-by-label credits.views/created-by-label)
(def created-by       credits.views/created-by)
(def credits          credits.views/component)

; site.components.frontend.scroll-sensor.views
(def scroll-sensor scroll-sensor.views/component)
