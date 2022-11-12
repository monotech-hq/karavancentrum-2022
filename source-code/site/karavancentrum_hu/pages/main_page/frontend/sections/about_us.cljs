
(ns site.karavancentrum-hu.pages.main-page.frontend.sections.about-us
    (:require [app.contents.frontend.api :as contents]
              [css.api                   :as css]
              [re-frame.api              :as r]
              [reagent.api               :refer [ratom]]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn about-us-page
  []
  (let [page-visible?  (ratom false)
        about-us-page @(r/subscribe [:db/get-item [:site :content :about-us-page]])]
       (fn [] [:<> [:div {:id :kc-about-us--section :style {:display (if-not @page-visible? "none" "block")}}
                         [contents/content-preview {:items     [about-us-page]
                                                    :style     {:color "#333"}
                                                    :font-size :m}]]
                   [:div {:class :kc-content-button :on-click #(swap! page-visible? not)}
                         (if @page-visible? "Kevesebb tartalom" "Tovább olvasom")]])))

(defn about-us-section
  []
  (let [about-us-section @(r/subscribe [:db/get-item [:site :content :about-us-section]])]
       [:div {:id :kc-about-us--section}
             [contents/content-preview {:font-size :m
                                        :items     [about-us-section]
                                        :style     {:color "#333"}}]]))

(defn about-us
  []
  [:div {:id :kc-about-us}
        [:div {:class :kc-section-title} "Magunkról"]
        [about-us-section]
        [about-us-page]])

(defn view
  []
  [:section {:id :about-us}
            [about-us]])
