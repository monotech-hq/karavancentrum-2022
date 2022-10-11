
(ns site.karavancentrum.pages.main-page.frontend.views
    (:require [app.common.frontend.api                                        :as common]
              [reagent.api                                                    :as reagent]
              [re-frame.api                                                   :as r]
              [site.karavancentrum.pages.main-page.frontend.sections.about-us :as about-us]
              [site.karavancentrum.pages.main-page.frontend.sections.brands   :as brands]
              [site.karavancentrum.pages.main-page.frontend.sections.contacts :as contacts]
              [site.karavancentrum.pages.main-page.frontend.sections.hero     :as hero]
              [site.karavancentrum.pages.main-page.frontend.sections.renting  :as renting]
              [tools.image-preloader.api                                      :as image-preloader]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn main-page
  [props]
  [:main#main-page--content [hero/view]
                            [renting/view]
                            [brands/view]
                            [about-us/view]
                            [contacts/view]
                            [:div {:style {:background "#2d2925"
                                           :padding "60px 0 15px 0"}}
                                  [common/credits {:theme :dark}]]
                            [image-preloader/component {:uri "/site/images/hero.jpg"}]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn view
  [scroll-target]
  (reagent/lifecycles
    {:component-did-mount (fn [] (r/dispatch [:utils/scroll-into scroll-target]))
     :reagent-render      (fn [scroll-target]
                             [main-page scroll-target])}))
